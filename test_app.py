"""Exercise the real console application in isolated session directories."""
from pathlib import Path
import shutil
import subprocess
import tempfile
import unittest
from run import ROOT, compile_sources


class AppTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        compile_sources()

    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.cwd = Path(self.temp.name)
        shutil.copytree(ROOT / "data", self.cwd / "data", ignore=shutil.ignore_patterns("userSessionData.csv"))

    def launch(self, commands="5\n", args=None):
        result = subprocess.run(["java", "-cp", str(ROOT / "build"), "StreamingApp", *(args if args is not None else ["TestUser", "3", "data/content.csv", "data/tvseries.txt"])], input=commands, text=True, capture_output=True, cwd=self.cwd, timeout=15)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertNotIn("Exception", result.stderr)
        return result.stdout

    def test_start_exit_and_session_restore(self):
        self.assertIn("Thank you for watching", self.launch())
        self.assertTrue((self.cwd / "data/userSessionData.csv").exists())
        self.assertIn("User Session Data File", self.launch())

    def test_watchlist_deduplication_and_playback_persistence(self):
        # Movies -> select first movie -> add twice -> play -> exit.
        output = self.launch("1\n1\nx\nw\nw\np\nq\nq\n3\n5\n")
        self.assertIn("already in watchlist", output)
        self.assertIn("previously watched list", output)
        restored = self.launch("4\n1\n4\n5\n")
        self.assertIn("Silent Pursuit", restored)

    def test_audio_playlist(self):
        output = self.launch("2\n1\nx\nw\nMy Mix\nw\nmy mix\nq\nq\n3\n5\n")
        self.assertIn("Playlist created", output)
        self.assertIn("already in playlist", output)
        self.assertIn("My Mix", self.launch("4\n3\nq\n4\n5\n"))

    def test_browse_all_canvases(self):
        output = self.launch("1\n2\nq\n3\n2\n2\nq\n3\n3\nq\n5\n")
        for title in ["TV SERIES", "PODCAST", "GAME"]:
            self.assertIn(title, output)

    def test_invalid_arguments_and_missing_file(self):
        self.assertIn("Invalid number of command-line arguments", self.launch(args=[]))
        self.assertIn("Invalid number of genres", self.launch(args=["Demo", "0", "data/content.csv", "data/tvseries.txt"]))
        self.assertIn("File Not Found", self.launch(args=["Demo", "3", "missing.csv", "data/tvseries.txt"]))

    def test_invalid_content_row_is_skipped(self):
        with (self.cwd / "data/content.csv").open("a") as file:
            file.write("\nMOVIE,broken,Invalid,bad,4,100,Creator,Description,ACTION\n")
        output = self.launch()
        self.assertIn("Invalid year format", output)
        self.assertIn("Thank you for watching", output)


if __name__ == "__main__":
    unittest.main(verbosity=2)
