function deleteGroup(pk){
	if(confirm("Are you sure?")){
		location.href="deleteGroup.html?pk="+pk;
	}
}

function addGroup(){
	location.href="addGroup.html";
}