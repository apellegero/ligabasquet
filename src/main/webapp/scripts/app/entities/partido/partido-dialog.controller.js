'use strict';

angular.module('ligabasquetApp').controller('PartidoDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Partido', 'Estadisticas', 'Arbitro', 'Temporada', 'Equipo',
        function($scope, $stateParams, $modalInstance, $q, entity, Partido, Estadisticas, Arbitro, Temporada, Equipo) {

        $scope.partido = entity;
        $scope.estadisticass = Estadisticas.query();
        $scope.arbitros = Arbitro.query();
        $scope.temporadas = Temporada.query();
        $scope.equipolocals = Equipo.query({filter: 'partido-is-null'});
        $q.all([$scope.partido.$promise, $scope.equipolocals.$promise]).then(function() {
            if (!$scope.partido.equipoLocal.id) {
                return $q.reject();
            }
            return Equipo.get({id : $scope.partido.equipoLocal.id}).$promise;
        }).then(function(equipoLocal) {
            $scope.equipolocals.push(equipoLocal);
        });
        $scope.equipovisitantes = Equipo.query({filter: 'partido-is-null'});
        $q.all([$scope.partido.$promise, $scope.equipovisitantes.$promise]).then(function() {
            if (!$scope.partido.equipoVisitante.id) {
                return $q.reject();
            }
            return Equipo.get({id : $scope.partido.equipoVisitante.id}).$promise;
        }).then(function(equipoVisitante) {
            $scope.equipovisitantes.push(equipoVisitante);
        });
        $scope.load = function(id) {
            Partido.get({id : id}, function(result) {
                $scope.partido = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('ligabasquetApp:partidoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.partido.id != null) {
                Partido.update($scope.partido, onSaveFinished);
            } else {
                Partido.save($scope.partido, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
