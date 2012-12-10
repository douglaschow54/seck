function search()
{
	var searchParam = document.forms["searchForm"]["searchInput"].value;
	if (searchParam==null || searchParam==""){
		alert("Please enter a parameter!");
		return false;
	}
	//alert("You Searched for: " + searchParam);
	showResults(searchParam);
}
function showResults(searched){
	 alert(searched);
	 
	 $.getJSON('sample.json', function(data) {
		 alert("Data Loaded!");
		});
	
}