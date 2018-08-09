import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Guis } from './guis.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Guis>;

@Injectable()
export class GuisService {

    private resourceUrl =  SERVER_API_URL + 'api/guis';

    constructor(private http: HttpClient) { }

    create(guis: Guis): Observable<EntityResponseType> {
        const copy = this.convert(guis);
        return this.http.post<Guis>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(guis: Guis): Observable<EntityResponseType> {
        const copy = this.convert(guis);
        return this.http.put<Guis>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Guis>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Guis[]>> {
        const options = createRequestOption(req);
        return this.http.get<Guis[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Guis[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Guis = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Guis[]>): HttpResponse<Guis[]> {
        const jsonResponse: Guis[] = res.body;
        const body: Guis[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Guis.
     */
    private convertItemFromServer(guis: Guis): Guis {
        const copy: Guis = Object.assign({}, guis);
        return copy;
    }

    /**
     * Convert a Guis to a JSON which can be sent to the server.
     */
    private convert(guis: Guis): Guis {
        const copy: Guis = Object.assign({}, guis);
        return copy;
    }
}
