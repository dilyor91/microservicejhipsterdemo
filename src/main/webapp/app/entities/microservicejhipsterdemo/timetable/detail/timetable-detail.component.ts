import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITimetable } from '../timetable.model';

@Component({
  selector: 'jhi-timetable-detail',
  templateUrl: './timetable-detail.component.html',
})
export class TimetableDetailComponent implements OnInit {
  timetable: ITimetable | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timetable }) => {
      this.timetable = timetable;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
