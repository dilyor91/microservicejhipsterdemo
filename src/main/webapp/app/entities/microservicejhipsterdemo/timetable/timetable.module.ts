import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TimetableComponent } from './list/timetable.component';
import { TimetableDetailComponent } from './detail/timetable-detail.component';
import { TimetableUpdateComponent } from './update/timetable-update.component';
import { TimetableDeleteDialogComponent } from './delete/timetable-delete-dialog.component';
import { TimetableRoutingModule } from './route/timetable-routing.module';

@NgModule({
  imports: [SharedModule, TimetableRoutingModule],
  declarations: [TimetableComponent, TimetableDetailComponent, TimetableUpdateComponent, TimetableDeleteDialogComponent],
})
export class MicroservicejhipsterdemoTimetableModule {}
