import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RegInstructuionComponent } from './list/reg-instructuion.component';
import { RegInstructuionDetailComponent } from './detail/reg-instructuion-detail.component';
import { RegInstructuionUpdateComponent } from './update/reg-instructuion-update.component';
import { RegInstructuionDeleteDialogComponent } from './delete/reg-instructuion-delete-dialog.component';
import { RegInstructuionRoutingModule } from './route/reg-instructuion-routing.module';

@NgModule({
  imports: [SharedModule, RegInstructuionRoutingModule],
  declarations: [
    RegInstructuionComponent,
    RegInstructuionDetailComponent,
    RegInstructuionUpdateComponent,
    RegInstructuionDeleteDialogComponent,
  ],
})
export class MicroservicejhipsterdemoRegInstructuionModule {}
