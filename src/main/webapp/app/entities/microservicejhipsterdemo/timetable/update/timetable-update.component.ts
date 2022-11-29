import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TimetableFormService, TimetableFormGroup } from './timetable-form.service';
import { ITimetable } from '../timetable.model';
import { TimetableService } from '../service/timetable.service';

@Component({
  selector: 'jhi-timetable-update',
  templateUrl: './timetable-update.component.html',
})
export class TimetableUpdateComponent implements OnInit {
  isSaving = false;
  timetable: ITimetable | null = null;

  editForm: TimetableFormGroup = this.timetableFormService.createTimetableFormGroup();

  constructor(
    protected timetableService: TimetableService,
    protected timetableFormService: TimetableFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timetable }) => {
      this.timetable = timetable;
      if (timetable) {
        this.updateForm(timetable);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const timetable = this.timetableFormService.getTimetable(this.editForm);
    if (timetable.id !== null) {
      this.subscribeToSaveResponse(this.timetableService.update(timetable));
    } else {
      this.subscribeToSaveResponse(this.timetableService.create(timetable));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimetable>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(timetable: ITimetable): void {
    this.timetable = timetable;
    this.timetableFormService.resetForm(this.editForm, timetable);
  }
}
