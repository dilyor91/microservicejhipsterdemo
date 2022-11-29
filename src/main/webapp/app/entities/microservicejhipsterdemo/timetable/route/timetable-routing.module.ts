import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TimetableComponent } from '../list/timetable.component';
import { TimetableDetailComponent } from '../detail/timetable-detail.component';
import { TimetableUpdateComponent } from '../update/timetable-update.component';
import { TimetableRoutingResolveService } from './timetable-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const timetableRoute: Routes = [
  {
    path: '',
    component: TimetableComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TimetableDetailComponent,
    resolve: {
      timetable: TimetableRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TimetableUpdateComponent,
    resolve: {
      timetable: TimetableRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TimetableUpdateComponent,
    resolve: {
      timetable: TimetableRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(timetableRoute)],
  exports: [RouterModule],
})
export class TimetableRoutingModule {}
