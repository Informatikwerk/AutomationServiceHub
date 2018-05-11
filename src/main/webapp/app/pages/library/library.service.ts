import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Library } from './library.model';
import { createRequestOption } from '../../shared';

export type LibraryResponseType = HttpResponse<Library>;
export type LibraryArrayResponseType = HttpResponse<Library[]>;

@Injectable()
export class LibraryService {

    private resourceUrl = SERVER_API_URL + 'api/library/library';

    constructor(private http: HttpClient) { }

    create(library: Library): Observable<LibraryResponseType> {
        const copy = this.convert(library);
        return this.http.post<Library>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: LibraryResponseType) => this.convertResponse(res));
    }

    update(library: Library): Observable<LibraryResponseType> {
        const copy = this.convert(library);
        return this.http.put<Library>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: LibraryResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<LibraryResponseType> {
        const options = createRequestOption(req);
        return this.http.get<Library>(this.resourceUrl, { observe: 'response' })
            .map((res: LibraryResponseType) => this.convertResponse(res));
    }

    private convertResponse(res: LibraryResponseType): LibraryResponseType {
        const body: Library = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: LibraryArrayResponseType): LibraryArrayResponseType {
        const jsonResponse: Library[] = res.body;
        const body: Library[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Library.
     */
    private convertItemFromServer(json: any): Library {
        const copy: Library = Object.assign(new Library(), json);
        return copy;
    }

    /**
     * Convert a Library to a JSON which can be sent to the server.
     */
    private convert(library: Library): Library {
        const copy: Library = Object.assign({}, library);
        return copy;
    }
}
