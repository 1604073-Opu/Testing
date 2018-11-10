<?php
$email = $_POST['email'];
$data->m=$email;
$pass= $_POST['pass'];
$data->p=$pass;
$con=mysqli_connect("localhost","ourcuet_zero","Cuetcse2016","ourcuet_zerodb");
$data->status=true;
if (mysqli_connect_errno($con)) {
    $data->status=false;
    echo json_encode($data);
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$result = mysqli_query($con,"SELECT * FROM login WHERE email='$email' AND password='$pass'");
$row = mysqli_fetch_array($result);
if(sizeOf($row)==0)
{
    $data->status="false";
}
echo json_encode($data);
mysqli_close($con);
?>