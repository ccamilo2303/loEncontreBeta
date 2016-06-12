/**
 * @autor crick
 * 06/06/2016
 */

var modulo = angular.module('loEncontreApp',['ngRoute'])
.config(['$routeProvider', function($routeProvider){
	$routeProvider
	.when('/', {
		templateUrl: '../templates/landinPage.html',
		controller: 'main'
	}).when('/dashBoard',{
		templateUrl: '../templates/dashBoard.html',
		controller: 'dashBoard'
	}).
	otherwise({
		redirectTo: '/addOrder'
	});

}]);

/**
 * Modulo que controla la landin page
 */
modulo.controller('main', function($scope,$http){
	$scope.load = false;
	$scope.labelBoton = 'Buscar';
	
	/*
	 * Busca el email
	 */
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
	
	/*
	 * Envia el mensaje de contacto
	 */
	$scope.enviarMensajeContacto = function(){
		 if($scope.nombreContacto == undefined ||  $scope.emailContacto == undefined  || $scope.mensajeContacto == undefined ){
			 new swal({   title: "Error!",   text: "Hay que llenar todos los campos",   type: "error",   confirmButtonText: "Cerrar" });
			 return;
		 }
		if($scope.nombreContacto.replace(' ','') == '' ||  $scope.emailContacto.replace(' ','') == '' || $scope.mensajeContacto.replace(' ','') == ''){
			new swal({   title: "Error!",   text: "Hay que llenar todos los campos",   type: "error",   confirmButtonText: "Cerrar" });
			return;
		}
		$scope.load = true;
		$http.get("../rest/enviarEmail/"+$scope.nombreContacto+"/"+$scope.emailContacto+"/"+$scope.mensajeContacto)
		.then(function(data){
			$scope.load = false;
			if(data.data.msg == true){
				new swal({   title: "Correcto",   text: "Gracias por contactarnos, pronto responderemos a tu solicitud.",   type: "info",   confirmButtonText: "Cerrar" });
			}else{
				new swal({   title: "Error!",   text: "El mensaje no pudo ser enviado, por favor intente mas tarde",   type: "error",   confirmButtonText: "Cerrar" });
			}
			$scope.nombreContacto = '';
			$scope.emailContacto = '';
			$scope.mensajeContacto = '';
		});
		
	}
})

modulo.controller('dashBoard', function($scope,$http, $location){
	$scope.view =  '../templates/inicioDashBoard.html';
	$scope.asignarView = function(a){
		$scope.view = '../templates/'+a+'.html';
	}
	console.log('ENTRO DAS -- ' +$location);
});

modulo.controller('findDocuments', function($scope,$http, $location){
	console.log('ENTRO CONTROLADOR FIND DOCUMENTS');
	$scope.documents = [
{"DESCRIPCION":"Hola", "FECHAREGISTRO":"20/05/2016", "PERDIDO":false},
{"DESCRIPCION":"Hola", "FECHAREGISTRO":"20/05/2016", "PERDIDO":false},
	                    {"DESCRIPCION":"Hola", "FECHAREGISTRO":"20/05/2016", "PERDIDO":false},
	                    {"DESCRIPCION":"Hsdfola", "FECHAREGISTRO":"20/05/2016", "PERDIDO":true}
	                    ];
	$scope.guardar = function(a){
		console.log(a.DESCRIPCION);
	}
});

function moveTo(to){
	var top = (to != 0 ? $("#"+to+"").offset().top-90:0);
	$('html, body').animate({
		scrollTop: top 
	}, 900);
}

