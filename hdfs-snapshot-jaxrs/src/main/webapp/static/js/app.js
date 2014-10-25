var app = angular.module('snapshotApp', []);

app.factory("model", function ($q, $timeout) {
  var getSnapshots = function () {
    var deferred = $q.defer();

    $timeout(function () {
      deferred.resolve({data: [
        {owner: "mike", path: "/user/mike/.snapshot/s1", date: "1414175016876"},
        {owner: "bob", path: "/user/bob/data/.snapshot/s2", date: "1414175016876"},
        {owner: "jen", path: "/user/jen/processed/aggregates/.snapshot/s1", date: "1414175016876"},
        {owner: "jen", path: "/user/jen/processed/aggregates/.snapshot/s2", date: "1414175016876"}
      ], status: 200});
    }, 2000);

    return deferred.promise;
  }

  return {
    getSnapshots: getSnapshots
  };
});

app.factory("model", function ($http) {
  var getUrl = function () {
    var parts = window.location.pathname.match(/\/[^\/]*/g);
    var view = parts[1];
    var version = '/versions' + parts[2];
    var instance = parts[3];
    if (parts.length == 4) { // version is not present
      instance = parts[2];
      version = '';
    }
    var namespaceUrl = '/api/v1/views' + view + version + '/instances' + instance + '/resources/snapshot';
    return namespaceUrl;
  };

  var getSnapshots = function () {
    return $http.get(getUrl());
  };

  return {
    getSnapshots: getSnapshots
  };
});

app.controller('snapshotController', function ($scope, $log, model) {
  $('.fa-spin').hide();
  $scope.refreshSnapshots = function () {
    $('.fa-spin').show();
    model.getSnapshots().then(function (response) {
      if (response.status === 204) {
        $scope.message = "No snapshots found";
        $scope.snapshots = "";
      }
      else {
        $scope.snapshots = response.data;
        $scope.message = "";
      }
      $('.fa-spin').hide();
    }, function (error) {
      $scope.message = "An error occurred loading snapshot directories from HDFS";
      $log.debug(error);
      $scope.snapshots = "";
      $('.fa-spin').hide();
    });
  }
});

