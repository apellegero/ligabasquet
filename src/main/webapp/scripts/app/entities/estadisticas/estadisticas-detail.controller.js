'use strict';

angular.module('ligabasquetApp')
    .controller('EstadisticasDetailController', function ($scope, $rootScope, $stateParams, entity, Estadisticas, Jugador) {
        $scope.estadisticas = entity;
        $scope.load = function (id) {
            Estadisticas.get({id: id}, function(result) {
                $scope.estadisticas = result;
            });
        };
        $rootScope.$on('ligabasquetApp:estadisticasUpdate', function(event, result) {
            $scope.estadisticas = result;
        });
    });
