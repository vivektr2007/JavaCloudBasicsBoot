function deleteRole(pk){
	if(confirm("Are you sure?")){
		location.href="deleteRole.html?pk="+pk;
	}
}

function addRole(){
	location.href="addRole.html";
}