USE [parcauto]
GO
SET IDENTITY_INSERT [dbo].[app_role] ON 

INSERT [dbo].[app_role] ([role_id], [role_name]) VALUES (2, N'ROLE_ACCES')
INSERT [dbo].[app_role] ([role_id], [role_name]) VALUES (1, N'ROLE_ADMIN')
INSERT [dbo].[app_role] ([role_id], [role_name]) VALUES (6, N'ROLE_MOYEN-GENERAUX')
INSERT [dbo].[app_role] ([role_id], [role_name]) VALUES (5, N'ROLE_PARCAUTO')
INSERT [dbo].[app_role] ([role_id], [role_name]) VALUES (4, N'ROLE_RESPONSABLE')
INSERT [dbo].[app_role] ([role_id], [role_name]) VALUES (3, N'ROLE_USER')
SET IDENTITY_INSERT [dbo].[app_role] OFF
