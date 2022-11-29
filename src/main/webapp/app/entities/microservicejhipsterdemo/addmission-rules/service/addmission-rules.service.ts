import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAddmissionRules, NewAddmissionRules } from '../addmission-rules.model';

export type PartialUpdateAddmissionRules = Partial<IAddmissionRules> & Pick<IAddmissionRules, 'id'>;

export type EntityResponseType = HttpResponse<IAddmissionRules>;
export type EntityArrayResponseType = HttpResponse<IAddmissionRules[]>;

@Injectable({ providedIn: 'root' })
export class AddmissionRulesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/addmission-rules', 'microservicejhipsterdemo');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(addmissionRules: NewAddmissionRules): Observable<EntityResponseType> {
    return this.http.post<IAddmissionRules>(this.resourceUrl, addmissionRules, { observe: 'response' });
  }

  update(addmissionRules: IAddmissionRules): Observable<EntityResponseType> {
    return this.http.put<IAddmissionRules>(`${this.resourceUrl}/${this.getAddmissionRulesIdentifier(addmissionRules)}`, addmissionRules, {
      observe: 'response',
    });
  }

  partialUpdate(addmissionRules: PartialUpdateAddmissionRules): Observable<EntityResponseType> {
    return this.http.patch<IAddmissionRules>(`${this.resourceUrl}/${this.getAddmissionRulesIdentifier(addmissionRules)}`, addmissionRules, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAddmissionRules>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAddmissionRules[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAddmissionRulesIdentifier(addmissionRules: Pick<IAddmissionRules, 'id'>): number {
    return addmissionRules.id;
  }

  compareAddmissionRules(o1: Pick<IAddmissionRules, 'id'> | null, o2: Pick<IAddmissionRules, 'id'> | null): boolean {
    return o1 && o2 ? this.getAddmissionRulesIdentifier(o1) === this.getAddmissionRulesIdentifier(o2) : o1 === o2;
  }

  addAddmissionRulesToCollectionIfMissing<Type extends Pick<IAddmissionRules, 'id'>>(
    addmissionRulesCollection: Type[],
    ...addmissionRulesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const addmissionRules: Type[] = addmissionRulesToCheck.filter(isPresent);
    if (addmissionRules.length > 0) {
      const addmissionRulesCollectionIdentifiers = addmissionRulesCollection.map(
        addmissionRulesItem => this.getAddmissionRulesIdentifier(addmissionRulesItem)!
      );
      const addmissionRulesToAdd = addmissionRules.filter(addmissionRulesItem => {
        const addmissionRulesIdentifier = this.getAddmissionRulesIdentifier(addmissionRulesItem);
        if (addmissionRulesCollectionIdentifiers.includes(addmissionRulesIdentifier)) {
          return false;
        }
        addmissionRulesCollectionIdentifiers.push(addmissionRulesIdentifier);
        return true;
      });
      return [...addmissionRulesToAdd, ...addmissionRulesCollection];
    }
    return addmissionRulesCollection;
  }
}
