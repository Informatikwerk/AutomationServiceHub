import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { NodeRegistry } from './node-registry.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<NodeRegistry>;

@Injectable()
export class NodeRegistryService {

    private resourceUrl =  SERVER_API_URL + 'api/node-registries';

    constructor(private http: HttpClient) { }

    create(nodeRegistry: NodeRegistry): Observable<EntityResponseType> {
        const copy = this.convert(nodeRegistry);
        return this.http.post<NodeRegistry>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(nodeRegistry: NodeRegistry): Observable<EntityResponseType> {
        const copy = this.convert(nodeRegistry);
        return this.http.put<NodeRegistry>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<NodeRegistry>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<NodeRegistry[]>> {
        const options = createRequestOption(req);
        return this.http.get<NodeRegistry[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<NodeRegistry[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: NodeRegistry = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<NodeRegistry[]>): HttpResponse<NodeRegistry[]> {
        const jsonResponse: NodeRegistry[] = res.body;
        const body: NodeRegistry[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to NodeRegistry.
     */
    private convertItemFromServer(nodeRegistry: NodeRegistry): NodeRegistry {
        const copy: NodeRegistry = Object.assign({}, nodeRegistry);
        return copy;
    }

    /**
     * Convert a NodeRegistry to a JSON which can be sent to the server.
     */
    private convert(nodeRegistry: NodeRegistry): NodeRegistry {
        const copy: NodeRegistry = Object.assign({}, nodeRegistry);
        return copy;
    }
}
