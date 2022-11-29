import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegInstructuion } from '../reg-instructuion.model';

@Component({
  selector: 'jhi-reg-instructuion-detail',
  templateUrl: './reg-instructuion-detail.component.html',
})
export class RegInstructuionDetailComponent implements OnInit {
  regInstructuion: IRegInstructuion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ regInstructuion }) => {
      this.regInstructuion = regInstructuion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
