(function() {
    'use strict';
    angular
        .module('espBrokerApp')
        .factory('NodeRegistry', NodeRegistry);

    NodeRegistry.$inject = ['$resource'];

    function NodeRegistry ($resource) {
        var resourceUrl =  'api/node-registries/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
