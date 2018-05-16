import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Sources } from './sources.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Sources>;

@Injectable()
export class SourcesService {

    private resourceUrl = SERVER_API_URL + 'api/sources';

    constructor(private http: HttpClient) {
    }

    create(sources: Sources): Observable<EntityResponseType> {
        console.log("saving sources");
        const copy = this.convert(sources);
        console.log("saving sources", copy);
        return this.http.post<Sources>(this.resourceUrl, copy, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(sources: Sources): Observable<EntityResponseType> {
        const copy = this.convert(sources);
        return this.http.put<Sources>(this.resourceUrl, copy, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Sources>(`${this.resourceUrl}/${id}`, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Sources[]>> {
        const options = createRequestOption(req);
        return this.http.get<Sources[]>(this.resourceUrl, {params: options, observe: 'response'})
            .map((res: HttpResponse<Sources[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Sources = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Sources[]>): HttpResponse<Sources[]> {
        const jsonResponse: Sources[] = res.body;
        const body: Sources[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Sources.
     */
    private convertItemFromServer(sources: Sources): Sources {
        const copy: Sources = Object.assign({}, sources);
        return copy;
    }

    /**
     * Convert a Sources to a JSON which can be sent to the server.
     */
    private convert(sources: Sources): Sources {
        const copy: Sources = Object.assign({}, sources);
        return copy;
    }
}
