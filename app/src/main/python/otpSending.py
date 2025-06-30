import smtplib
from email.mime.text import MIMEText
import random



def send_otp_email(receiver_email: str) -> int:
    """Send a 6‑digit OTP to the given email and return the generated OTP."""
    # 1️⃣ Generate a 6‑digit OTP
    otp = random.randint(100000, 999999)

    # 2️⃣ Email details
    sender_email = "godofgenjutsu890@gmail.com"
    app_password = "abc def "  # Your generated app password
    subject = "Your OTP Verification Code"
    body = f"Your verification code is: {otp}"

    # 3️⃣ Create the email
    msg = MIMEText(body)
    msg["Subject"] = subject
    msg["From"] = sender_email
    msg["To"] = receiver_email

    # 4️⃣ Connect and send
    with smtplib.SMTP_SSL("smtp.gmail.com", 465) as server:
        server.login(sender_email, app_password)
        server.send_message(msg)

    print(f"✅ OTP {otp} sent to {receiver_email}")
    return otp
