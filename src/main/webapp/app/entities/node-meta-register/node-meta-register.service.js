(function() {
    'use strict';
    angular
        .module('automationServiceHubApp')
        .factory('NodeMetaRegister', NodeMetaRegister);

    NodeMetaRegister.$inject = ['$resource'];

    function NodeMetaRegister ($resource) {
        var resourceUrl =  'api/node-meta-registers/:id';

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
