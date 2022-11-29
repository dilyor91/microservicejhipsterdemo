import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RegInstructuionDetailComponent } from './reg-instructuion-detail.component';

describe('RegInstructuion Management Detail Component', () => {
  let comp: RegInstructuionDetailComponent;
  let fixture: ComponentFixture<RegInstructuionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegInstructuionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ regInstructuion: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RegInstructuionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RegInstructuionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load regInstructuion on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.regInstructuion).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
