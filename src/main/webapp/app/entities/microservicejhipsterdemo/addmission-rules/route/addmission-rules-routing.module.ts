import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AddmissionRulesComponent } from '../list/addmission-rules.component';
import { AddmissionRulesDetailComponent } from '../detail/addmission-rules-detail.component';
import { AddmissionRulesUpdateComponent } from '../update/addmission-rules-update.component';
import { AddmissionRulesRoutingResolveService } from './addmission-rules-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const addmissionRulesRoute: Routes = [
  {
    path: '',
    component: AddmissionRulesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AddmissionRulesDetailComponent,
    resolve: {
      addmissionRules: AddmissionRulesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AddmissionRulesUpdateComponent,
    resolve: {
      addmissionRules: AddmissionRulesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AddmissionRulesUpdateComponent,
    resolve: {
      addmissionRules: AddmissionRulesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(addmissionRulesRoute)],
  exports: [RouterModule],
})
export class AddmissionRulesRoutingModule {}
