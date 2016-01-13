'use strict';

angular.module('ligabasquetApp')
    .controller('PartidoDetailController', function ($scope, $rootScope, $stateParams, entity, Partido, Estadisticas, Arbitro, Temporada, Equipo) {
        $scope.partido = entity;
        $scope.load = function (id) {
            Partido.get({id: id}, function(result) {
                $scope.partido = result;
            });
        };
        $rootScope.$on('ligabasquetApp:partidoUpdate', function(event, result) {
            $scope.partido = result;
        });
    });
