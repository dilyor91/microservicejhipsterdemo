import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRegInstructuion, NewRegInstructuion } from '../reg-instructuion.model';

export type PartialUpdateRegInstructuion = Partial<IRegInstructuion> & Pick<IRegInstructuion, 'id'>;

export type EntityResponseType = HttpResponse<IRegInstructuion>;
export type EntityArrayResponseType = HttpResponse<IRegInstructuion[]>;

@Injectable({ providedIn: 'root' })
export class RegInstructuionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/reg-instructuions', 'microservicejhipsterdemo');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(regInstructuion: NewRegInstructuion): Observable<EntityResponseType> {
    return this.http.post<IRegInstructuion>(this.resourceUrl, regInstructuion, { observe: 'response' });
  }

  update(regInstructuion: IRegInstructuion): Observable<EntityResponseType> {
    return this.http.put<IRegInstructuion>(`${this.resourceUrl}/${this.getRegInstructuionIdentifier(regInstructuion)}`, regInstructuion, {
      observe: 'response',
    });
  }

  partialUpdate(regInstructuion: PartialUpdateRegInstructuion): Observable<EntityResponseType> {
    return this.http.patch<IRegInstructuion>(`${this.resourceUrl}/${this.getRegInstructuionIdentifier(regInstructuion)}`, regInstructuion, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRegInstructuion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRegInstructuion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRegInstructuionIdentifier(regInstructuion: Pick<IRegInstructuion, 'id'>): number {
    return regInstructuion.id;
  }

  compareRegInstructuion(o1: Pick<IRegInstructuion, 'id'> | null, o2: Pick<IRegInstructuion, 'id'> | null): boolean {
    return o1 && o2 ? this.getRegInstructuionIdentifier(o1) === this.getRegInstructuionIdentifier(o2) : o1 === o2;
  }

  addRegInstructuionToCollectionIfMissing<Type extends Pick<IRegInstructuion, 'id'>>(
    regInstructuionCollection: Type[],
    ...regInstructuionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const regInstructuions: Type[] = regInstructuionsToCheck.filter(isPresent);
    if (regInstructuions.length > 0) {
      const regInstructuionCollectionIdentifiers = regInstructuionCollection.map(
        regInstructuionItem => this.getRegInstructuionIdentifier(regInstructuionItem)!
      );
      const regInstructuionsToAdd = regInstructuions.filter(regInstructuionItem => {
        const regInstructuionIdentifier = this.getRegInstructuionIdentifier(regInstructuionItem);
        if (regInstructuionCollectionIdentifiers.includes(regInstructuionIdentifier)) {
          return false;
        }
        regInstructuionCollectionIdentifiers.push(regInstructuionIdentifier);
        return true;
      });
      return [...regInstructuionsToAdd, ...regInstructuionCollection];
    }
    return regInstructuionCollection;
  }
}
