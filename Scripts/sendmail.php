<?php
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;
require 'PHPMailer/src/Exception.php';
require 'PHPMailer/src/PHPMailer.php';
require 'PHPMailer/src/SMTP.php';
$temp->status=false;
$mail = new PHPMailer(true);
$name = $_POST['name'];
$address=$_POST['email'];
$code=$_POST['code'];
$message = 'Hello,'.$name.'\nYour verification code is'.$code.'\nThank you for using.';
$subject = 'E-mail Verification';
$mail->isSMTP();
$mail->SMTPDebug=true;
$mail->SMTPAuth = true;
$mail->SMTPSecure = 'ssl';
$mail->Port = 465;
$mail->Host = 'premium37.web-hosting.com';
$mail->Username = 'help@zero.ourcuet.com';
$mail->Password = 'nahidulopu';
$mail->setFrom('help@zero.ourcuet.com');
$mail->addAddress($address);
$mail->Subject = $subject;
$mail->msgHtml($message);
if(!$mail->Send()) {
    echo "Mailer Error: " . $mail->ErrorInfo;
}
else {
    $temp->status=true;
    echo "Message has been sent successfully";
}
echo json_encode($temp);
?>