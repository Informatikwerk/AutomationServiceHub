import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { LibraryRegistry } from './library-registry.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<LibraryRegistry>;

@Injectable()
export class LibraryRegistryService {

    private resourceUrl =  SERVER_API_URL + 'api/library-registries';

    constructor(private http: HttpClient) { }

    create(libraryRegistry: LibraryRegistry): Observable<EntityResponseType> {
        const copy = this.convert(libraryRegistry);
        return this.http.post<LibraryRegistry>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(libraryRegistry: LibraryRegistry): Observable<EntityResponseType> {
        const copy = this.convert(libraryRegistry);
        return this.http.put<LibraryRegistry>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LibraryRegistry>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LibraryRegistry[]>> {
        const options = createRequestOption(req);
        return this.http.get<LibraryRegistry[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LibraryRegistry[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LibraryRegistry = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LibraryRegistry[]>): HttpResponse<LibraryRegistry[]> {
        const jsonResponse: LibraryRegistry[] = res.body;
        const body: LibraryRegistry[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LibraryRegistry.
     */
    private convertItemFromServer(libraryRegistry: LibraryRegistry): LibraryRegistry {
        const copy: LibraryRegistry = Object.assign({}, libraryRegistry);
        return copy;
    }

    /**
     * Convert a LibraryRegistry to a JSON which can be sent to the server.
     */
    private convert(libraryRegistry: LibraryRegistry): LibraryRegistry {
        const copy: LibraryRegistry = Object.assign({}, libraryRegistry);
        return copy;
    }
}
