<h1>Here is whats in the headers:</h1>

<?php
foreach($_SERVER as $key_name => $key_value) {
    print $key_name . " = " . $key_value . "<br>";
}
?>