import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { RegInstructuionFormService, RegInstructuionFormGroup } from './reg-instructuion-form.service';
import { IRegInstructuion } from '../reg-instructuion.model';
import { RegInstructuionService } from '../service/reg-instructuion.service';

@Component({
  selector: 'jhi-reg-instructuion-update',
  templateUrl: './reg-instructuion-update.component.html',
})
export class RegInstructuionUpdateComponent implements OnInit {
  isSaving = false;
  regInstructuion: IRegInstructuion | null = null;

  editForm: RegInstructuionFormGroup = this.regInstructuionFormService.createRegInstructuionFormGroup();

  constructor(
    protected regInstructuionService: RegInstructuionService,
    protected regInstructuionFormService: RegInstructuionFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ regInstructuion }) => {
      this.regInstructuion = regInstructuion;
      if (regInstructuion) {
        this.updateForm(regInstructuion);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const regInstructuion = this.regInstructuionFormService.getRegInstructuion(this.editForm);
    if (regInstructuion.id !== null) {
      this.subscribeToSaveResponse(this.regInstructuionService.update(regInstructuion));
    } else {
      this.subscribeToSaveResponse(this.regInstructuionService.create(regInstructuion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegInstructuion>>): void {
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

  protected updateForm(regInstructuion: IRegInstructuion): void {
    this.regInstructuion = regInstructuion;
    this.regInstructuionFormService.resetForm(this.editForm, regInstructuion);
  }
}
