import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TimetableFormService } from './timetable-form.service';
import { TimetableService } from '../service/timetable.service';
import { ITimetable } from '../timetable.model';

import { TimetableUpdateComponent } from './timetable-update.component';

describe('Timetable Management Update Component', () => {
  let comp: TimetableUpdateComponent;
  let fixture: ComponentFixture<TimetableUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let timetableFormService: TimetableFormService;
  let timetableService: TimetableService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TimetableUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TimetableUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TimetableUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    timetableFormService = TestBed.inject(TimetableFormService);
    timetableService = TestBed.inject(TimetableService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const timetable: ITimetable = { id: 456 };

      activatedRoute.data = of({ timetable });
      comp.ngOnInit();

      expect(comp.timetable).toEqual(timetable);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITimetable>>();
      const timetable = { id: 123 };
      jest.spyOn(timetableFormService, 'getTimetable').mockReturnValue(timetable);
      jest.spyOn(timetableService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timetable });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: timetable }));
      saveSubject.complete();

      // THEN
      expect(timetableFormService.getTimetable).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(timetableService.update).toHaveBeenCalledWith(expect.objectContaining(timetable));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITimetable>>();
      const timetable = { id: 123 };
      jest.spyOn(timetableFormService, 'getTimetable').mockReturnValue({ id: null });
      jest.spyOn(timetableService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timetable: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: timetable }));
      saveSubject.complete();

      // THEN
      expect(timetableFormService.getTimetable).toHaveBeenCalled();
      expect(timetableService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITimetable>>();
      const timetable = { id: 123 };
      jest.spyOn(timetableService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timetable });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(timetableService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
