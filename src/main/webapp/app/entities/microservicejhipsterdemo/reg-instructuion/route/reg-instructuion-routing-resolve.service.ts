import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRegInstructuion } from '../reg-instructuion.model';
import { RegInstructuionService } from '../service/reg-instructuion.service';

@Injectable({ providedIn: 'root' })
export class RegInstructuionRoutingResolveService implements Resolve<IRegInstructuion | null> {
  constructor(protected service: RegInstructuionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRegInstructuion | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((regInstructuion: HttpResponse<IRegInstructuion>) => {
          if (regInstructuion.body) {
            return of(regInstructuion.body);
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
