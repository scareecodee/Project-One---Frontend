
from yt_dlp import YoutubeDL

def get_video_info(video_url: str) -> dict:
    """Get thumbnail URL and available video quality options (just like 144p, 720p)."""
    ydl_opts = {'quiet': True, 'skip_download': True}
    with YoutubeDL(ydl_opts) as ydl:
        info = ydl.extract_info(video_url, download=False)

    thumbnail = info.get('thumbnail')
    resolutions = []
    for f in info.get('formats', []):
        if f.get('vcodec') != 'none':  # It's a video stream
            height = f.get('height')
            if height:
                res_label = f"{height}p"
                if res_label not in resolutions:
                    resolutions.append(res_label)

    return {
        "thumbnail": thumbnail,
        "resolutions": sorted(resolutions, key=lambda x: int(x.replace("p", "")))  # sorted order
    }


# Usage
data = get_video_info("https://www.youtube.com/watch?v=1EaSKSlK2to")
print(data["resolutions"])


thumbnail=(get_video_info("https://www.youtube.com/watch?v=z4Kb8iHjXYg"))["thumbnail"]


import requests
import os

def download_thumbnail(thumbnail_url: str, save_dir: str = ".", filename: str = "thumbnail.jpg") -> str:
    """
    Downloads the thumbnail from the given URL and saves it.

    Args:
        thumbnail_url (str): The thumbnail URL (you already have this).
        save_dir (str): Directory where you want to save it.
        filename (str): Name of the saved thumbnail file.

    Returns:
        str: The full path where the thumbnail was saved.
    """
    response = requests.get(thumbnail_url, stream=True)
    if response.status_code == 200:
        os.makedirs(save_dir, exist_ok=True)
        save_path = os.path.join(save_dir, filename)
        with open(save_path, 'wb') as f:
            for chunk in response.iter_content(1024):
                f.write(chunk)
        return save_path
    else:
        raise Exception(f"Error downloading thumbnail, status code: {response.status_code}")


download_thumbnail(thumbnail)



from yt_dlp import YoutubeDL
import os

def download_video(video_url: str, desired_quality: str, save_dir: str = ".", filename: str = None) -> str:
    """
    Downloads a YouTube video at the specified quality.

    Args:
        video_url (str): The YouTube video URL.
        desired_quality (str): The quality required (e.g., '720p', '1080p').
        save_dir (str): Directory where the video should be saved.
        filename (str): Optional. Name of the saved video file.

    Returns:
        str: The full path where the video was saved.
    """
    # Extract video information
    ydl_opts = {'quiet': True, 'skip_download': True}
    with YoutubeDL(ydl_opts) as ydl:
        info = ydl.extract_info(video_url, download=False)

    # Find the best matching format id
    format_id = None
    for f in info.get('formats', []):
        if f.get('vcodec') != 'none':  # Ensure it's a video stream
            height = f.get('height')
            if height and f"{height}p" == desired_quality:
                format_id = f.get('format_id')
                break

    if not format_id:
        raise Exception(f"Requested quality ({desired_quality}) not found.")

    # Ensure save_dir exists
    os.makedirs(save_dir, exist_ok=True)

    # Build the output filename if not provided
    if not filename:
        filename = f"{info.get('title', 'video')}_{desired_quality}.mp4"

    save_path = os.path.join(save_dir, filename)

    # Download the video
    ydl_download_opts = {
        'format': format_id,
        'outtmpl': save_path,
        'quiet': True
    }

    with YoutubeDL(ydl_download_opts) as ydl:
        ydl.download([video_url])

    return save_path


# Usage example:
# path = download_video("https://www.youtube.com/watch?v=1EaSKSlK2to", "720p", save_dir="./Downloads")
# print(f"Downloaded to: {path}")

