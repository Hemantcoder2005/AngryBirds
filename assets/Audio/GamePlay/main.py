import os
from pydub import AudioSegment
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler

# Folder to monitor and convert files
FOLDER_PATH = os.getcwd()

# Set to track already converted files
converted_files = set()


def initialize_converted_files():
    """Initialize the set of converted files by scanning for .wav files."""
    global converted_files
    for file_name in os.listdir(FOLDER_PATH):
        if file_name.endswith(".wav"):
            # Add the corresponding .mp3 file name to the set
            mp3_file = os.path.splitext(file_name)[0] + ".mp3"
            converted_files.add(mp3_file)


def convert_to_wav(mp3_path):
    """Convert MP3 file to WAV format."""
    wav_path = os.path.splitext(mp3_path)[0] + ".wav"
    if mp3_path in converted_files:
        print(f"Already converted: {mp3_path}")
        return

    try:
        audio = AudioSegment.from_mp3(mp3_path)
        audio.export(wav_path, format="wav")
        print(f"Converted: {mp3_path} to {wav_path}")

        # Add to the converted files set
        converted_files.add(mp3_path)
    except Exception as e:
        print(f"Error converting {mp3_path}: {e}")


class MP3Handler(FileSystemEventHandler):
    """Handles new MP3 files added to the folder."""
    def on_created(self, event):
        if event.is_directory:
            return
        if event.src_path.endswith(".mp3"):
            convert_to_wav(event.src_path)


def scan_existing_files():
    """Scan the folder for unconverted MP3 files and convert them."""
    for file_name in os.listdir(FOLDER_PATH):
        file_path = os.path.join(FOLDER_PATH, file_name)
        if file_name.endswith(".mp3") and file_path not in converted_files:
            convert_to_wav(file_path)


def monitor_folder():
    """Monitor folder for new MP3 files."""
    observer = Observer()
    event_handler = MP3Handler()
    observer.schedule(event_handler, path=FOLDER_PATH, recursive=False)
    observer.start()
    print(f"Monitoring folder: {FOLDER_PATH}")
    try:
        observer.join()
    except KeyboardInterrupt:
        observer.stop()
    observer.join()


if __name__ == "__main__":
    # Initialize converted files set
    initialize_converted_files()
    # Convert existing files before monitoring
    scan_existing_files()
    # Start monitoring for new files
    monitor_folder()
