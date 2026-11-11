"""Compile and launch StreamFlix from any working directory (JDK 17+)."""
from pathlib import Path
import subprocess
import sys

ROOT = Path(__file__).resolve().parent


def compile_sources():
    (ROOT / "build").mkdir(exist_ok=True)
    sources = sorted(str(p) for p in (ROOT / "src").rglob("*.java"))
    subprocess.run(["java", "com.sun.tools.javac.Main", "-encoding", "UTF-8", "-d", str(ROOT / "build"), *sources], check=True)


if __name__ == "__main__":
    compile_sources()
    (ROOT / "data").mkdir(exist_ok=True)
    args = sys.argv[1:] or ["Demo", "3", "data/content.csv", "data/tvseries.txt"]
    raise SystemExit(subprocess.call(["java", "-cp", str(ROOT / "build"), "StreamingApp", *args], cwd=ROOT))
