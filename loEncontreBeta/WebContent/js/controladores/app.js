var modulo = angular.module('loEncontreApp',['ngRoute'])
.config(['$routeProvider', function($routeProvider){
	$routeProvider
	.when('/', {
		templateUrl: '../templates/landinPage.html',
		controller: 'main'
	}).
	otherwise({
		redirectTo: '/addOrder'
	});

}]);

modulo.controller('main', function($scope,$http){
	$scope.load = false;
	$scope.labelBoton = 'Buscar';
	$scope.validarEmail = function(){
		var email = $scope.emailLogin;
		if(email == undefined){
			return;
		}
		$scope.load = true;
		$scope.labelBoton = 'Buscando';

		$http.get("../rest/encontrarDocumentos/"+$scope.emailLogin)
		.then(function(data){

			console.log(data.data);
			$scope.load = false;
			$scope.labelBoton = 'Buscar';
			if(data.data.msg == true){
				new swal({   title: "Error!",   text: data.data.message,   type: "error",   confirmButtonText: "Cerrar" });
			}
		});
	}
})


function moveTo(to){
	console.log(to+' ---- ');
	$('html, body').animate({
		scrollTop: $("#"+to+"").offset().top
	}, 800);
}

