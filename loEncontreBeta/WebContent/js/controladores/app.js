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

modulo.controller('main', function($scope){
	
})


