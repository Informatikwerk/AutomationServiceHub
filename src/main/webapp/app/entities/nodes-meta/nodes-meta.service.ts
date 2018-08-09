import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { NodesMeta } from './nodes-meta.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<NodesMeta>;

@Injectable()
export class NodesMetaService {

    private resourceUrl =  SERVER_API_URL + 'api/nodes-metas';

    constructor(private http: HttpClient) { }

    create(nodesMeta: NodesMeta): Observable<EntityResponseType> {
        const copy = this.convert(nodesMeta);
        return this.http.post<NodesMeta>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(nodesMeta: NodesMeta): Observable<EntityResponseType> {
        const copy = this.convert(nodesMeta);
        return this.http.put<NodesMeta>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<NodesMeta>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<NodesMeta[]>> {
        const options = createRequestOption(req);
        return this.http.get<NodesMeta[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<NodesMeta[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: NodesMeta = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<NodesMeta[]>): HttpResponse<NodesMeta[]> {
        const jsonResponse: NodesMeta[] = res.body;
        const body: NodesMeta[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to NodesMeta.
     */
    private convertItemFromServer(nodesMeta: NodesMeta): NodesMeta {
        const copy: NodesMeta = Object.assign({}, nodesMeta);
        return copy;
    }

    /**
     * Convert a NodesMeta to a JSON which can be sent to the server.
     */
    private convert(nodesMeta: NodesMeta): NodesMeta {
        const copy: NodesMeta = Object.assign({}, nodesMeta);
        return copy;
    }
}
