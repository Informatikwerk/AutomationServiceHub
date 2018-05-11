import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { NodeMetaRegistry } from './node-meta-registry.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<NodeMetaRegistry>;

@Injectable()
export class NodeMetaRegistryService {

    private resourceUrl =  SERVER_API_URL + 'api/node-meta-registries';

    constructor(private http: HttpClient) { }

    create(nodeMetaRegistry: NodeMetaRegistry): Observable<EntityResponseType> {
        const copy = this.convert(nodeMetaRegistry);
        return this.http.post<NodeMetaRegistry>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(nodeMetaRegistry: NodeMetaRegistry): Observable<EntityResponseType> {
        const copy = this.convert(nodeMetaRegistry);
        return this.http.put<NodeMetaRegistry>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<NodeMetaRegistry>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<NodeMetaRegistry[]>> {
        const options = createRequestOption(req);
        return this.http.get<NodeMetaRegistry[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<NodeMetaRegistry[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: NodeMetaRegistry = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<NodeMetaRegistry[]>): HttpResponse<NodeMetaRegistry[]> {
        const jsonResponse: NodeMetaRegistry[] = res.body;
        const body: NodeMetaRegistry[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to NodeMetaRegistry.
     */
    private convertItemFromServer(nodeMetaRegistry: NodeMetaRegistry): NodeMetaRegistry {
        const copy: NodeMetaRegistry = Object.assign({}, nodeMetaRegistry);
        return copy;
    }

    /**
     * Convert a NodeMetaRegistry to a JSON which can be sent to the server.
     */
    private convert(nodeMetaRegistry: NodeMetaRegistry): NodeMetaRegistry {
        const copy: NodeMetaRegistry = Object.assign({}, nodeMetaRegistry);
        return copy;
    }
}
