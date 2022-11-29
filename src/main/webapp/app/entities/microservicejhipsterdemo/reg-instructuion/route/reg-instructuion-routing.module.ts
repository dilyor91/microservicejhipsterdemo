import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RegInstructuionComponent } from '../list/reg-instructuion.component';
import { RegInstructuionDetailComponent } from '../detail/reg-instructuion-detail.component';
import { RegInstructuionUpdateComponent } from '../update/reg-instructuion-update.component';
import { RegInstructuionRoutingResolveService } from './reg-instructuion-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const regInstructuionRoute: Routes = [
  {
    path: '',
    component: RegInstructuionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RegInstructuionDetailComponent,
    resolve: {
      regInstructuion: RegInstructuionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RegInstructuionUpdateComponent,
    resolve: {
      regInstructuion: RegInstructuionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RegInstructuionUpdateComponent,
    resolve: {
      regInstructuion: RegInstructuionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(regInstructuionRoute)],
  exports: [RouterModule],
})
export class RegInstructuionRoutingModule {}
