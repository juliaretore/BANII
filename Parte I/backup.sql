toc.dat                                                                                             0000600 0004000 0002000 00000050227 14407114445 0014451 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        PGDMP       !                    {         
   biblioteca     14.7 (Ubuntu 14.7-1.pgdg22.04+1)     15.2 (Ubuntu 15.2-1.pgdg22.04+1) E    |           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false         }           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false         ~           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                    1262    42407 
   biblioteca    DATABASE     v   CREATE DATABASE biblioteca WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.UTF-8';
    DROP DATABASE biblioteca;
                postgres    false                     2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                postgres    false         �           0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    4         �            1259    42409    autor    TABLE     �   CREATE TABLE public.autor (
    id integer NOT NULL,
    nacionalidade character varying(100) NOT NULL,
    area character varying(100) NOT NULL,
    nome character varying(100) NOT NULL
);
    DROP TABLE public.autor;
       public         heap    postgres    false    4         �            1259    42423    autores_livro    TABLE     d   CREATE TABLE public.autores_livro (
    id_livro integer NOT NULL,
    id_autor integer NOT NULL
);
 !   DROP TABLE public.autores_livro;
       public         heap    postgres    false    4         �            1259    42438 	   categoria    TABLE     �   CREATE TABLE public.categoria (
    id integer NOT NULL,
    nome character varying(100) NOT NULL,
    tempo_empr integer NOT NULL,
    qnt_max_empr integer NOT NULL
);
    DROP TABLE public.categoria;
       public         heap    postgres    false    4         �            1259    42517 
   emprestimo    TABLE     T  CREATE TABLE public.emprestimo (
    id integer NOT NULL,
    multa real NOT NULL,
    renovacoes integer NOT NULL,
    data_empr date NOT NULL,
    data_est_entr date NOT NULL,
    data_real_entr date,
    id_exemplar integer NOT NULL,
    id_usuario integer NOT NULL,
    id_funcionario integer NOT NULL,
    situacao integer NOT NULL
);
    DROP TABLE public.emprestimo;
       public         heap    postgres    false    4         �            1259    42444    endereco    TABLE     3  CREATE TABLE public.endereco (
    id integer NOT NULL,
    estado character varying(2) NOT NULL,
    cidade character varying(100) NOT NULL,
    bairro character varying(100) NOT NULL,
    rua character varying(100) NOT NULL,
    numero integer NOT NULL,
    complemento character varying(300) NOT NULL
);
    DROP TABLE public.endereco;
       public         heap    postgres    false    4         �            1259    42478    exemplar    TABLE     �   CREATE TABLE public.exemplar (
    id integer NOT NULL,
    prateleira integer NOT NULL,
    estante integer NOT NULL,
    id_livro integer NOT NULL,
    id_usuario_reserva integer,
    data_reserva date
);
    DROP TABLE public.exemplar;
       public         heap    postgres    false    4         �            1259    42494    funcionario    TABLE       CREATE TABLE public.funcionario (
    id integer NOT NULL,
    login character varying(100) NOT NULL,
    senha character varying(100) NOT NULL,
    nome character varying(100) NOT NULL,
    salario real NOT NULL,
    turno character varying(40) NOT NULL,
    tipo integer NOT NULL
);
    DROP TABLE public.funcionario;
       public         heap    postgres    false    4         �            1259    42408    id_autor    SEQUENCE     q   CREATE SEQUENCE public.id_autor
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.id_autor;
       public          postgres    false    4         �            1259    42422    id_autores_livro    SEQUENCE     y   CREATE SEQUENCE public.id_autores_livro
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.id_autores_livro;
       public          postgres    false    4         �            1259    42516    id_emprestimo    SEQUENCE     v   CREATE SEQUENCE public.id_emprestimo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.id_emprestimo;
       public          postgres    false    4         �            1259    42443    id_endereco    SEQUENCE     t   CREATE SEQUENCE public.id_endereco
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.id_endereco;
       public          postgres    false    4         �            1259    42477    id_exemplar    SEQUENCE     t   CREATE SEQUENCE public.id_exemplar
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.id_exemplar;
       public          postgres    false    4         �            1259    42493    id_funcionario    SEQUENCE     w   CREATE SEQUENCE public.id_funcionario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.id_funcionario;
       public          postgres    false    4         �            1259    42414    id_livro    SEQUENCE     q   CREATE SEQUENCE public.id_livro
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.id_livro;
       public          postgres    false    4         �            1259    42451 
   id_usuario    SEQUENCE     s   CREATE SEQUENCE public.id_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.id_usuario;
       public          postgres    false    4         �            1259    42415    livro    TABLE     �   CREATE TABLE public.livro (
    id integer NOT NULL,
    isbn character varying(50),
    titulo character varying(100) NOT NULL,
    colecao character varying(100),
    editora character varying(100) NOT NULL
);
    DROP TABLE public.livro;
       public         heap    postgres    false    4         �            1259    42501 
   supervisao    TABLE     n   CREATE TABLE public.supervisao (
    id_assistente integer NOT NULL,
    id_bibliotecario integer NOT NULL
);
    DROP TABLE public.supervisao;
       public         heap    postgres    false    4         �            1259    42467    telefone    TABLE     m   CREATE TABLE public.telefone (
    id_usuario integer NOT NULL,
    numero character varying(15) NOT NULL
);
    DROP TABLE public.telefone;
       public         heap    postgres    false    4         �            1259    42452    usuario    TABLE     �   CREATE TABLE public.usuario (
    id integer NOT NULL,
    nome character varying(100) NOT NULL,
    endereco integer NOT NULL,
    turno character varying(40) NOT NULL,
    id_categoria integer NOT NULL
);
    DROP TABLE public.usuario;
       public         heap    postgres    false    4         h          0    42409    autor 
   TABLE DATA           >   COPY public.autor (id, nacionalidade, area, nome) FROM stdin;
    public          postgres    false    210       3432.dat l          0    42423    autores_livro 
   TABLE DATA           ;   COPY public.autores_livro (id_livro, id_autor) FROM stdin;
    public          postgres    false    214       3436.dat m          0    42438 	   categoria 
   TABLE DATA           G   COPY public.categoria (id, nome, tempo_empr, qnt_max_empr) FROM stdin;
    public          postgres    false    215       3437.dat y          0    42517 
   emprestimo 
   TABLE DATA           �   COPY public.emprestimo (id, multa, renovacoes, data_empr, data_est_entr, data_real_entr, id_exemplar, id_usuario, id_funcionario, situacao) FROM stdin;
    public          postgres    false    227       3449.dat o          0    42444    endereco 
   TABLE DATA           X   COPY public.endereco (id, estado, cidade, bairro, rua, numero, complemento) FROM stdin;
    public          postgres    false    217       3439.dat t          0    42478    exemplar 
   TABLE DATA           g   COPY public.exemplar (id, prateleira, estante, id_livro, id_usuario_reserva, data_reserva) FROM stdin;
    public          postgres    false    222       3444.dat v          0    42494    funcionario 
   TABLE DATA           S   COPY public.funcionario (id, login, senha, nome, salario, turno, tipo) FROM stdin;
    public          postgres    false    224       3446.dat j          0    42415    livro 
   TABLE DATA           C   COPY public.livro (id, isbn, titulo, colecao, editora) FROM stdin;
    public          postgres    false    212       3434.dat w          0    42501 
   supervisao 
   TABLE DATA           E   COPY public.supervisao (id_assistente, id_bibliotecario) FROM stdin;
    public          postgres    false    225       3447.dat r          0    42467    telefone 
   TABLE DATA           6   COPY public.telefone (id_usuario, numero) FROM stdin;
    public          postgres    false    220       3442.dat q          0    42452    usuario 
   TABLE DATA           J   COPY public.usuario (id, nome, endereco, turno, id_categoria) FROM stdin;
    public          postgres    false    219       3441.dat �           0    0    id_autor    SEQUENCE SET     6   SELECT pg_catalog.setval('public.id_autor', 2, true);
          public          postgres    false    209         �           0    0    id_autores_livro    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.id_autores_livro', 1, false);
          public          postgres    false    213         �           0    0    id_emprestimo    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.id_emprestimo', 1, true);
          public          postgres    false    226         �           0    0    id_endereco    SEQUENCE SET     9   SELECT pg_catalog.setval('public.id_endereco', 2, true);
          public          postgres    false    216         �           0    0    id_exemplar    SEQUENCE SET     9   SELECT pg_catalog.setval('public.id_exemplar', 9, true);
          public          postgres    false    221         �           0    0    id_funcionario    SEQUENCE SET     <   SELECT pg_catalog.setval('public.id_funcionario', 4, true);
          public          postgres    false    223         �           0    0    id_livro    SEQUENCE SET     6   SELECT pg_catalog.setval('public.id_livro', 4, true);
          public          postgres    false    211         �           0    0 
   id_usuario    SEQUENCE SET     8   SELECT pg_catalog.setval('public.id_usuario', 2, true);
          public          postgres    false    218         �           2606    42413    autor autor_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.autor
    ADD CONSTRAINT autor_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.autor DROP CONSTRAINT autor_pkey;
       public            postgres    false    210         �           2606    42427     autores_livro autores_livro_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.autores_livro
    ADD CONSTRAINT autores_livro_pkey PRIMARY KEY (id_livro, id_autor);
 J   ALTER TABLE ONLY public.autores_livro DROP CONSTRAINT autores_livro_pkey;
       public            postgres    false    214    214         �           2606    42442    categoria categoria_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.categoria DROP CONSTRAINT categoria_pkey;
       public            postgres    false    215         �           2606    42521    emprestimo emprestimo_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.emprestimo
    ADD CONSTRAINT emprestimo_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.emprestimo DROP CONSTRAINT emprestimo_pkey;
       public            postgres    false    227         �           2606    42450    endereco endereco_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.endereco DROP CONSTRAINT endereco_pkey;
       public            postgres    false    217         �           2606    42482    exemplar exemplar_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.exemplar
    ADD CONSTRAINT exemplar_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.exemplar DROP CONSTRAINT exemplar_pkey;
       public            postgres    false    222         �           2606    42500 !   funcionario funcionario_login_key 
   CONSTRAINT     ]   ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_login_key UNIQUE (login);
 K   ALTER TABLE ONLY public.funcionario DROP CONSTRAINT funcionario_login_key;
       public            postgres    false    224         �           2606    42498    funcionario funcionario_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.funcionario DROP CONSTRAINT funcionario_pkey;
       public            postgres    false    224         �           2606    42421    livro livro_isbn_key 
   CONSTRAINT     O   ALTER TABLE ONLY public.livro
    ADD CONSTRAINT livro_isbn_key UNIQUE (isbn);
 >   ALTER TABLE ONLY public.livro DROP CONSTRAINT livro_isbn_key;
       public            postgres    false    212         �           2606    42419    livro livro_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.livro
    ADD CONSTRAINT livro_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.livro DROP CONSTRAINT livro_pkey;
       public            postgres    false    212         �           2606    42505    supervisao supervisao_pkey 
   CONSTRAINT     u   ALTER TABLE ONLY public.supervisao
    ADD CONSTRAINT supervisao_pkey PRIMARY KEY (id_assistente, id_bibliotecario);
 D   ALTER TABLE ONLY public.supervisao DROP CONSTRAINT supervisao_pkey;
       public            postgres    false    225    225         �           2606    42471    telefone telefone_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.telefone
    ADD CONSTRAINT telefone_pkey PRIMARY KEY (id_usuario, numero);
 @   ALTER TABLE ONLY public.telefone DROP CONSTRAINT telefone_pkey;
       public            postgres    false    220    220         �           2606    42456    usuario usuario_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
       public            postgres    false    219         �           2606    42433 )   autores_livro autores_livro_id_autor_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.autores_livro
    ADD CONSTRAINT autores_livro_id_autor_fkey FOREIGN KEY (id_autor) REFERENCES public.autor(id);
 S   ALTER TABLE ONLY public.autores_livro DROP CONSTRAINT autores_livro_id_autor_fkey;
       public          postgres    false    3255    214    210         �           2606    42428 )   autores_livro autores_livro_id_livro_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.autores_livro
    ADD CONSTRAINT autores_livro_id_livro_fkey FOREIGN KEY (id_livro) REFERENCES public.livro(id);
 S   ALTER TABLE ONLY public.autores_livro DROP CONSTRAINT autores_livro_id_livro_fkey;
       public          postgres    false    214    3259    212         �           2606    42522 &   emprestimo emprestimo_id_exemplar_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.emprestimo
    ADD CONSTRAINT emprestimo_id_exemplar_fkey FOREIGN KEY (id_exemplar) REFERENCES public.exemplar(id);
 P   ALTER TABLE ONLY public.emprestimo DROP CONSTRAINT emprestimo_id_exemplar_fkey;
       public          postgres    false    3271    227    222         �           2606    42532 )   emprestimo emprestimo_id_funcionario_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.emprestimo
    ADD CONSTRAINT emprestimo_id_funcionario_fkey FOREIGN KEY (id_funcionario) REFERENCES public.funcionario(id);
 S   ALTER TABLE ONLY public.emprestimo DROP CONSTRAINT emprestimo_id_funcionario_fkey;
       public          postgres    false    224    3275    227         �           2606    42527 %   emprestimo emprestimo_id_usuario_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.emprestimo
    ADD CONSTRAINT emprestimo_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);
 O   ALTER TABLE ONLY public.emprestimo DROP CONSTRAINT emprestimo_id_usuario_fkey;
       public          postgres    false    3267    227    219         �           2606    42488    exemplar exemplar_id_livro_fkey    FK CONSTRAINT        ALTER TABLE ONLY public.exemplar
    ADD CONSTRAINT exemplar_id_livro_fkey FOREIGN KEY (id_livro) REFERENCES public.livro(id);
 I   ALTER TABLE ONLY public.exemplar DROP CONSTRAINT exemplar_id_livro_fkey;
       public          postgres    false    212    222    3259         �           2606    42483 )   exemplar exemplar_id_usuario_reserva_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.exemplar
    ADD CONSTRAINT exemplar_id_usuario_reserva_fkey FOREIGN KEY (id_usuario_reserva) REFERENCES public.usuario(id);
 S   ALTER TABLE ONLY public.exemplar DROP CONSTRAINT exemplar_id_usuario_reserva_fkey;
       public          postgres    false    3267    219    222         �           2606    42506 (   supervisao supervisao_id_assistente_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.supervisao
    ADD CONSTRAINT supervisao_id_assistente_fkey FOREIGN KEY (id_assistente) REFERENCES public.funcionario(id);
 R   ALTER TABLE ONLY public.supervisao DROP CONSTRAINT supervisao_id_assistente_fkey;
       public          postgres    false    225    224    3275         �           2606    42511 +   supervisao supervisao_id_bibliotecario_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.supervisao
    ADD CONSTRAINT supervisao_id_bibliotecario_fkey FOREIGN KEY (id_bibliotecario) REFERENCES public.funcionario(id);
 U   ALTER TABLE ONLY public.supervisao DROP CONSTRAINT supervisao_id_bibliotecario_fkey;
       public          postgres    false    224    3275    225         �           2606    42472 !   telefone telefone_id_usuario_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.telefone
    ADD CONSTRAINT telefone_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);
 K   ALTER TABLE ONLY public.telefone DROP CONSTRAINT telefone_id_usuario_fkey;
       public          postgres    false    3267    219    220         �           2606    42462    usuario usuario_endereco_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_endereco_fkey FOREIGN KEY (endereco) REFERENCES public.endereco(id);
 G   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_endereco_fkey;
       public          postgres    false    219    217    3265         �           2606    42457 !   usuario usuario_id_categoria_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoria(id);
 K   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_id_categoria_fkey;
       public          postgres    false    215    3263    219                                                                                                                                                                                                                                                                                                                                                                                 3432.dat                                                                                            0000600 0004000 0002000 00000000130 14407114445 0014243 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	Canadense	Matemática	James Stewart
2	Norte Americano	Computação	James Stewart
\.


                                                                                                                                                                                                                                                                                                                                                                                                                                        3436.dat                                                                                            0000600 0004000 0002000 00000000025 14407114445 0014252 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	1
2	1
3	1
4	2
\.


                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           3437.dat                                                                                            0000600 0004000 0002000 00000000161 14407114445 0014254 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	Aluno Graduação	15	6
2	Aluno Pós-Graduação	30	8
3	Professor	30	10
4	Professor Pós-Graduação	90	10
\.


                                                                                                                                                                                                                                                                                                                                                                                                               3449.dat                                                                                            0000600 0004000 0002000 00000000054 14407114445 0014260 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	0	0	2023-03-13	2023-03-28	\N	1	1	1	0
\.


                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    3439.dat                                                                                            0000600 0004000 0002000 00000000204 14407114445 0014254 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	SC	Joinville	Zona Industrial Norte	Rua Paulo Malschitzki	100	
2	SC	Joinville	Zona Industrial Norte	Rua Paulo Malschitzki	80	
\.


                                                                                                                                                                                                                                                                                                                                                                                            3444.dat                                                                                            0000600 0004000 0002000 00000000203 14407114445 0014247 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	1	5	1	\N	\N
2	1	5	1	\N	\N
3	1	5	1	\N	\N
4	2	5	1	\N	\N
5	2	5	1	\N	\N
6	3	5	1	\N	\N
7	3	5	1	\N	\N
8	4	6	3	\N	\N
9	4	6	3	\N	\N
\.


                                                                                                                                                                                                                                                                                                                                                                                             3446.dat                                                                                            0000600 0004000 0002000 00000000253 14407114445 0014256 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	ana.lucia	123	Ana Lucia	3200	Vespertino	1
2	fernando	123	Fernando	3200	Matutino	1
3	ana.maria	123	Ana Maria	2000	Vespertino	2
4	liliane	123	Liliane	2000	Matutino	2
\.


                                                                                                                                                                                                                                                                                                                                                     3434.dat                                                                                            0000600 0004000 0002000 00000000354 14407114445 0014255 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	978-34-83943-01-3	Cálculo I	Permanente	cengage
2	934-54-82548-41-4	Cálculo II	Permanente	cengage
3	902-34-44484-78-1	Cálculo III	Permanente	cengage
4	865-54-53859-54-3	Introdução à Teoria da Computação	Permanente	cengage
\.


                                                                                                                                                                                                                                                                                    3447.dat                                                                                            0000600 0004000 0002000 00000000015 14407114445 0014253 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        3	1
4	2
\.


                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   3442.dat                                                                                            0000600 0004000 0002000 00000000062 14407114445 0014250 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	49 991346459
1	49 991081563
2	47 991836492
\.


                                                                                                                                                                                                                                                                                                                                                                                                                                                                              3441.dat                                                                                            0000600 0004000 0002000 00000000077 14407114445 0014255 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	Julia Retore	1	Integral	1
2	Giovana Correa	2	Integral	1
\.


                                                                                                                                                                                                                                                                                                                                                                                                                                                                 restore.sql                                                                                         0000600 0004000 0002000 00000040445 14407114445 0015377 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        --
-- NOTE:
--
-- File paths need to be edited. Search for $$PATH$$ and
-- replace it with the path to the directory containing
-- the extracted data files.
--
--
-- PostgreSQL database dump
--

-- Dumped from database version 14.7 (Ubuntu 14.7-1.pgdg22.04+1)
-- Dumped by pg_dump version 15.2 (Ubuntu 15.2-1.pgdg22.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE biblioteca;
--
-- Name: biblioteca; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE biblioteca WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.UTF-8';


ALTER DATABASE biblioteca OWNER TO postgres;

\connect biblioteca

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: autor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.autor (
    id integer NOT NULL,
    nacionalidade character varying(100) NOT NULL,
    area character varying(100) NOT NULL,
    nome character varying(100) NOT NULL
);


ALTER TABLE public.autor OWNER TO postgres;

--
-- Name: autores_livro; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.autores_livro (
    id_livro integer NOT NULL,
    id_autor integer NOT NULL
);


ALTER TABLE public.autores_livro OWNER TO postgres;

--
-- Name: categoria; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria (
    id integer NOT NULL,
    nome character varying(100) NOT NULL,
    tempo_empr integer NOT NULL,
    qnt_max_empr integer NOT NULL
);


ALTER TABLE public.categoria OWNER TO postgres;

--
-- Name: emprestimo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.emprestimo (
    id integer NOT NULL,
    multa real NOT NULL,
    renovacoes integer NOT NULL,
    data_empr date NOT NULL,
    data_est_entr date NOT NULL,
    data_real_entr date,
    id_exemplar integer NOT NULL,
    id_usuario integer NOT NULL,
    id_funcionario integer NOT NULL,
    situacao integer NOT NULL
);


ALTER TABLE public.emprestimo OWNER TO postgres;

--
-- Name: endereco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.endereco (
    id integer NOT NULL,
    estado character varying(2) NOT NULL,
    cidade character varying(100) NOT NULL,
    bairro character varying(100) NOT NULL,
    rua character varying(100) NOT NULL,
    numero integer NOT NULL,
    complemento character varying(300) NOT NULL
);


ALTER TABLE public.endereco OWNER TO postgres;

--
-- Name: exemplar; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.exemplar (
    id integer NOT NULL,
    prateleira integer NOT NULL,
    estante integer NOT NULL,
    id_livro integer NOT NULL,
    id_usuario_reserva integer,
    data_reserva date
);


ALTER TABLE public.exemplar OWNER TO postgres;

--
-- Name: funcionario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.funcionario (
    id integer NOT NULL,
    login character varying(100) NOT NULL,
    senha character varying(100) NOT NULL,
    nome character varying(100) NOT NULL,
    salario real NOT NULL,
    turno character varying(40) NOT NULL,
    tipo integer NOT NULL
);


ALTER TABLE public.funcionario OWNER TO postgres;

--
-- Name: id_autor; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.id_autor
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.id_autor OWNER TO postgres;

--
-- Name: id_autores_livro; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.id_autores_livro
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.id_autores_livro OWNER TO postgres;

--
-- Name: id_emprestimo; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.id_emprestimo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.id_emprestimo OWNER TO postgres;

--
-- Name: id_endereco; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.id_endereco
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.id_endereco OWNER TO postgres;

--
-- Name: id_exemplar; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.id_exemplar
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.id_exemplar OWNER TO postgres;

--
-- Name: id_funcionario; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.id_funcionario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.id_funcionario OWNER TO postgres;

--
-- Name: id_livro; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.id_livro
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.id_livro OWNER TO postgres;

--
-- Name: id_usuario; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.id_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.id_usuario OWNER TO postgres;

--
-- Name: livro; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.livro (
    id integer NOT NULL,
    isbn character varying(50),
    titulo character varying(100) NOT NULL,
    colecao character varying(100),
    editora character varying(100) NOT NULL
);


ALTER TABLE public.livro OWNER TO postgres;

--
-- Name: supervisao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.supervisao (
    id_assistente integer NOT NULL,
    id_bibliotecario integer NOT NULL
);


ALTER TABLE public.supervisao OWNER TO postgres;

--
-- Name: telefone; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.telefone (
    id_usuario integer NOT NULL,
    numero character varying(15) NOT NULL
);


ALTER TABLE public.telefone OWNER TO postgres;

--
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id integer NOT NULL,
    nome character varying(100) NOT NULL,
    endereco integer NOT NULL,
    turno character varying(40) NOT NULL,
    id_categoria integer NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- Data for Name: autor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.autor (id, nacionalidade, area, nome) FROM stdin;
\.
COPY public.autor (id, nacionalidade, area, nome) FROM '$$PATH$$/3432.dat';

--
-- Data for Name: autores_livro; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.autores_livro (id_livro, id_autor) FROM stdin;
\.
COPY public.autores_livro (id_livro, id_autor) FROM '$$PATH$$/3436.dat';

--
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categoria (id, nome, tempo_empr, qnt_max_empr) FROM stdin;
\.
COPY public.categoria (id, nome, tempo_empr, qnt_max_empr) FROM '$$PATH$$/3437.dat';

--
-- Data for Name: emprestimo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.emprestimo (id, multa, renovacoes, data_empr, data_est_entr, data_real_entr, id_exemplar, id_usuario, id_funcionario, situacao) FROM stdin;
\.
COPY public.emprestimo (id, multa, renovacoes, data_empr, data_est_entr, data_real_entr, id_exemplar, id_usuario, id_funcionario, situacao) FROM '$$PATH$$/3449.dat';

--
-- Data for Name: endereco; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.endereco (id, estado, cidade, bairro, rua, numero, complemento) FROM stdin;
\.
COPY public.endereco (id, estado, cidade, bairro, rua, numero, complemento) FROM '$$PATH$$/3439.dat';

--
-- Data for Name: exemplar; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.exemplar (id, prateleira, estante, id_livro, id_usuario_reserva, data_reserva) FROM stdin;
\.
COPY public.exemplar (id, prateleira, estante, id_livro, id_usuario_reserva, data_reserva) FROM '$$PATH$$/3444.dat';

--
-- Data for Name: funcionario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.funcionario (id, login, senha, nome, salario, turno, tipo) FROM stdin;
\.
COPY public.funcionario (id, login, senha, nome, salario, turno, tipo) FROM '$$PATH$$/3446.dat';

--
-- Data for Name: livro; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.livro (id, isbn, titulo, colecao, editora) FROM stdin;
\.
COPY public.livro (id, isbn, titulo, colecao, editora) FROM '$$PATH$$/3434.dat';

--
-- Data for Name: supervisao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.supervisao (id_assistente, id_bibliotecario) FROM stdin;
\.
COPY public.supervisao (id_assistente, id_bibliotecario) FROM '$$PATH$$/3447.dat';

--
-- Data for Name: telefone; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.telefone (id_usuario, numero) FROM stdin;
\.
COPY public.telefone (id_usuario, numero) FROM '$$PATH$$/3442.dat';

--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (id, nome, endereco, turno, id_categoria) FROM stdin;
\.
COPY public.usuario (id, nome, endereco, turno, id_categoria) FROM '$$PATH$$/3441.dat';

--
-- Name: id_autor; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.id_autor', 2, true);


--
-- Name: id_autores_livro; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.id_autores_livro', 1, false);


--
-- Name: id_emprestimo; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.id_emprestimo', 1, true);


--
-- Name: id_endereco; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.id_endereco', 2, true);


--
-- Name: id_exemplar; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.id_exemplar', 9, true);


--
-- Name: id_funcionario; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.id_funcionario', 4, true);


--
-- Name: id_livro; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.id_livro', 4, true);


--
-- Name: id_usuario; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.id_usuario', 2, true);


--
-- Name: autor autor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autor
    ADD CONSTRAINT autor_pkey PRIMARY KEY (id);


--
-- Name: autores_livro autores_livro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autores_livro
    ADD CONSTRAINT autores_livro_pkey PRIMARY KEY (id_livro, id_autor);


--
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id);


--
-- Name: emprestimo emprestimo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emprestimo
    ADD CONSTRAINT emprestimo_pkey PRIMARY KEY (id);


--
-- Name: endereco endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);


--
-- Name: exemplar exemplar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exemplar
    ADD CONSTRAINT exemplar_pkey PRIMARY KEY (id);


--
-- Name: funcionario funcionario_login_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_login_key UNIQUE (login);


--
-- Name: funcionario funcionario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_pkey PRIMARY KEY (id);


--
-- Name: livro livro_isbn_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livro
    ADD CONSTRAINT livro_isbn_key UNIQUE (isbn);


--
-- Name: livro livro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livro
    ADD CONSTRAINT livro_pkey PRIMARY KEY (id);


--
-- Name: supervisao supervisao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.supervisao
    ADD CONSTRAINT supervisao_pkey PRIMARY KEY (id_assistente, id_bibliotecario);


--
-- Name: telefone telefone_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.telefone
    ADD CONSTRAINT telefone_pkey PRIMARY KEY (id_usuario, numero);


--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- Name: autores_livro autores_livro_id_autor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autores_livro
    ADD CONSTRAINT autores_livro_id_autor_fkey FOREIGN KEY (id_autor) REFERENCES public.autor(id);


--
-- Name: autores_livro autores_livro_id_livro_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autores_livro
    ADD CONSTRAINT autores_livro_id_livro_fkey FOREIGN KEY (id_livro) REFERENCES public.livro(id);


--
-- Name: emprestimo emprestimo_id_exemplar_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emprestimo
    ADD CONSTRAINT emprestimo_id_exemplar_fkey FOREIGN KEY (id_exemplar) REFERENCES public.exemplar(id);


--
-- Name: emprestimo emprestimo_id_funcionario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emprestimo
    ADD CONSTRAINT emprestimo_id_funcionario_fkey FOREIGN KEY (id_funcionario) REFERENCES public.funcionario(id);


--
-- Name: emprestimo emprestimo_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emprestimo
    ADD CONSTRAINT emprestimo_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- Name: exemplar exemplar_id_livro_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exemplar
    ADD CONSTRAINT exemplar_id_livro_fkey FOREIGN KEY (id_livro) REFERENCES public.livro(id);


--
-- Name: exemplar exemplar_id_usuario_reserva_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exemplar
    ADD CONSTRAINT exemplar_id_usuario_reserva_fkey FOREIGN KEY (id_usuario_reserva) REFERENCES public.usuario(id);


--
-- Name: supervisao supervisao_id_assistente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.supervisao
    ADD CONSTRAINT supervisao_id_assistente_fkey FOREIGN KEY (id_assistente) REFERENCES public.funcionario(id);


--
-- Name: supervisao supervisao_id_bibliotecario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.supervisao
    ADD CONSTRAINT supervisao_id_bibliotecario_fkey FOREIGN KEY (id_bibliotecario) REFERENCES public.funcionario(id);


--
-- Name: telefone telefone_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.telefone
    ADD CONSTRAINT telefone_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- Name: usuario usuario_endereco_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_endereco_fkey FOREIGN KEY (endereco) REFERENCES public.endereco(id);


--
-- Name: usuario usuario_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoria(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           