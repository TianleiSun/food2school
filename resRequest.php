<?php

    // $con = mysqli_connect("my_host", "my_user", "my_password", "my_database");
    $con = mysqli_connect("localhost", "id976937_hyu2", "03291856", "id976937_hacktechdb");
    
    $location =  $_POST["location"];
    
    $statement = mysqli_prepare($con, "SELECT restaurant.restaurantName, restaurant.restaurantPhone, restaurant.restaurantAddress, restaurant.restaurantID,  restaurant.photo FROM post NATURAL JOIN restaurant WHERE post.destination = ? and post.valid = 1");
    mysqli_stmt_bind_param($statement, "s", $location);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $colrestaurantName, $colrestaurantPhone, $colrestaurantAddress, $colrestaurantID, $colrestaurantPhoto);
    
    
    $response = array();
    $response["success"] = true;
    $response["restaurantName"]=array();
    $response["restaurantPhone"] = array();
    $response["restaurantAddress"] = array();
    $response["restaurantID"] = array();
    $response["restaurantPhoto"] = array();
    while(mysqli_stmt_fetch($statement))
    {   
        array_push($response["restaurantName"], $colrestaurantName);
        array_push($response["restaurantPhone"], $colrestaurantPhone);
        array_push($response["restaurantAddress"], $colrestaurantAddress);
        array_push($response["restaurantID"], $colrestaurantID);
        array_push($response["restaurantPhoto"], $colrestaurantPhoto);
    }

    echo json_encode($response);
?>