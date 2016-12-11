<?php
	switch ($_POST['username']) {
		case 'aaa':
			if ($_POST['password']=='111') {
				echo "1";
			}else{
				echo "-1";
			}
			break;
		case 'bbb':
			if ($_POST['password']=='222') {
				echo "1";
			}else{
				echo "-1";
			}
			break;
		case 'ccc':
			if ($_POST['password']=='222') {
				echo "1";
			}else{
				echo "-1";
			}
			break;
		default:
			echo "0";
			break;
	}
?>