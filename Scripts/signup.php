<?php
$email = $_POST['email'];
$pass = $_POST['pass'];
$name = $_POST['name'];
$dob = $_POST['dob'];
$gender=$_POST['gender'];
$temp->status = "not ok";
$con = mysqli_connect("localhost", "ourcuet_zero", "Cuetcse2016", "ourcuet_zerodb");
if (mysqli_connect_errno($con)) {
    $temp->status="connection error";
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
} else {
    $result = mysqli_query($con, "SELECT * FROM login WHERE email='$email' AND password='$pass'");
    $row = mysqli_fetch_row($result);
    $temp->status="exists";
    if (sizeOf($row) == 0) {
        $temp->status = "ok";
        $con->query($con,"INSERT INTO user(email,name,dob,gender) VALUES('$email','$name','$dob','$gender')");
        $con->query( $con,"INSERT INTO login(email,password) VALUES('$email','$pass')");

    }
}
echo json_encode($temp);
mysqli_close($con);
?>