import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Nodes } from './nodes.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Nodes>;

@Injectable()
export class NodesService {

    private resourceUrl =  SERVER_API_URL + 'api/nodes';

    constructor(private http: HttpClient) { }

    create(nodes: Nodes): Observable<EntityResponseType> {
        const copy = this.convert(nodes);
        return this.http.post<Nodes>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(nodes: Nodes): Observable<EntityResponseType> {
        const copy = this.convert(nodes);
        return this.http.put<Nodes>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Nodes>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Nodes[]>> {
        const options = createRequestOption(req);
        return this.http.get<Nodes[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Nodes[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Nodes = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Nodes[]>): HttpResponse<Nodes[]> {
        const jsonResponse: Nodes[] = res.body;
        const body: Nodes[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Nodes.
     */
    private convertItemFromServer(nodes: Nodes): Nodes {
        const copy: Nodes = Object.assign({}, nodes);
        return copy;
    }

    /**
     * Convert a Nodes to a JSON which can be sent to the server.
     */
    private convert(nodes: Nodes): Nodes {
        const copy: Nodes = Object.assign({}, nodes);
        return copy;
    }
}
