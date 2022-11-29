import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAddmissionRules } from '../addmission-rules.model';
import { AddmissionRulesService } from '../service/addmission-rules.service';

@Injectable({ providedIn: 'root' })
export class AddmissionRulesRoutingResolveService implements Resolve<IAddmissionRules | null> {
  constructor(protected service: AddmissionRulesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAddmissionRules | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((addmissionRules: HttpResponse<IAddmissionRules>) => {
          if (addmissionRules.body) {
            return of(addmissionRules.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
