export interface IRegInstructuion {
  id: number;
  titleUz?: string | null;
  titleRu?: string | null;
  titleKr?: string | null;
  contentUz?: string | null;
  contentRu?: string | null;
  contentKr?: string | null;
  status?: boolean | null;
}

export type NewRegInstructuion = Omit<IRegInstructuion, 'id'> & { id: null };
