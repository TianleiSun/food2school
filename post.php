<?php

    $connect = mysqli_connect("localhost", "id976937_hyu2", "03291856", "id976937_hacktechdb");

    if ( !$connect ) {
        die( 'connect error: '.mysqli_connect_error() );
    }
    
    $resName = "boiling point";
    $driverID = 4;
    $restID = 3;
    $targetAddress = "cats";
    $deliveryTime = "12:00";
    $maxOrder = 5;

    // $resName = $_POST["resName"];
    // $driverID = (int)$_POST["driverID"];
    // $restID = (int)$_POST["restID"];
    // $targetAddress = $_POST["targetAddress"];
    // $deliveryTime = $_POST["deliveryTime"];
    // $maxOrder = (int)$_POST["maxOrder"]

     function addPost() {
        global $connect, $resName, $driverID, $restID, $targetAddress, $deliveryTime, $maxOrder;
        $stmt = mysqli_prepare($connect, "INSERT INTO post (driverID, restaurantID, destination, delivery, maxOrder, valid) VALUES (?, ?, ?, ?, ?, 1)") or die(mysqli_error($connect));;
        mysqli_stmt_bind_param($stmt, "iissi", $driverID, $restID, $targetAddress, $deliveryTime, $maxOrder);
        mysqli_stmt_execute($stmt);
        mysqli_stmt_close($stmt);     
    }

    function restAvailable() {
        global $connect, $restID;
        $stmt = mysqli_prepare($connect, "SELECT * FROM restaurant WHERE restID = ?"); 
        mysqli_stmt_bind_param($stmt, "i", $restID);
        mysqli_stmt_execute($stmt);
        mysqli_stmt_store_result($stmt);
        $count = mysqli_stmt_num_rows($stmt);
        mysqli_stmt_close($stmt); 
        if ($count < 1){
            return true; 
        }else {
            return false; 
        }
    }

    $response = array();
    $response["success"] = false;  

    if (restAvailable()){
        addPost();
        $response["success"] = true;  
    }
    
    echo json_encode($response);
?>