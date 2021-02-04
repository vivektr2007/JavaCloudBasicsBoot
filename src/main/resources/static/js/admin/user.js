function deleteUser(pk){
	if(confirm("Are you sure?")){
		location.href="deleteUser.html?pk="+pk;
	}
}

function addUser(){
	location.href="addUser.html";
}
function resetPassword(pk){
	if(confirm("Are you sure?")){
		location.href="resetPassword.html?pk="+pk;
	}
}