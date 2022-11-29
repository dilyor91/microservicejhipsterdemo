import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AddmissionRulesComponent } from './list/addmission-rules.component';
import { AddmissionRulesDetailComponent } from './detail/addmission-rules-detail.component';
import { AddmissionRulesUpdateComponent } from './update/addmission-rules-update.component';
import { AddmissionRulesDeleteDialogComponent } from './delete/addmission-rules-delete-dialog.component';
import { AddmissionRulesRoutingModule } from './route/addmission-rules-routing.module';

@NgModule({
  imports: [SharedModule, AddmissionRulesRoutingModule],
  declarations: [
    AddmissionRulesComponent,
    AddmissionRulesDetailComponent,
    AddmissionRulesUpdateComponent,
    AddmissionRulesDeleteDialogComponent,
  ],
})
export class MicroservicejhipsterdemoAddmissionRulesModule {}
