import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITimetable } from '../timetable.model';
import { TimetableService } from '../service/timetable.service';

@Injectable({ providedIn: 'root' })
export class TimetableRoutingResolveService implements Resolve<ITimetable | null> {
  constructor(protected service: TimetableService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITimetable | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((timetable: HttpResponse<ITimetable>) => {
          if (timetable.body) {
            return of(timetable.body);
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
