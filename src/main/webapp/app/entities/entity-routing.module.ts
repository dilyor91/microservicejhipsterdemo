import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'reg-instructuion',
        data: { pageTitle: 'microservicejhipsterdemoApp.microservicejhipsterdemoRegInstructuion.home.title' },
        loadChildren: () =>
          import('./microservicejhipsterdemo/reg-instructuion/reg-instructuion.module').then(
            m => m.MicroservicejhipsterdemoRegInstructuionModule
          ),
      },
      {
        path: 'addmission-rules',
        data: { pageTitle: 'microservicejhipsterdemoApp.microservicejhipsterdemoAddmissionRules.home.title' },
        loadChildren: () =>
          import('./microservicejhipsterdemo/addmission-rules/addmission-rules.module').then(
            m => m.MicroservicejhipsterdemoAddmissionRulesModule
          ),
      },
      {
        path: 'timetable',
        data: { pageTitle: 'microservicejhipsterdemoApp.microservicejhipsterdemoTimetable.home.title' },
        loadChildren: () =>
          import('./microservicejhipsterdemo/timetable/timetable.module').then(m => m.MicroservicejhipsterdemoTimetableModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
