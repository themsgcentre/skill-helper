CREATE TABLE [dbo].[Visibility] (
    [Id] [bigint] IDENTITY(1,1) PRIMARY KEY,
    [Description] [nvarchar](100) NOT NULL
);

INSERT INTO [dbo].[Visibility] ([Description]) VALUES
    (N'Private'),
    (N'Public'),
    (N'Friends-Only');

CREATE TABLE [dbo].[Skill] (
    [Id] [bigint] IDENTITY(1,1) PRIMARY KEY,
    [Name] [nvarchar](200) NOT NULL,
    [Description] nvarchar(max) NOT NULL,
    [StressLevel] [int] NOT NULL,
    [Author] [nvarchar](16) NULL,
    [Visibility] [bigint] NOT NULL,
    [ImageSrc] [nvarchar](max) NULL,
    CONSTRAINT [FK_AUTHOR] FOREIGN KEY ([Author]) REFERENCES [User]([Username]),
    CONSTRAINT [FK_VISIBILITY] FOREIGN KEY ([Visibility]) REFERENCES [Visibility]([Id])
);

CREATE TABLE [dbo].[Favorite] (
    [User] [nvarchar](16),
    [Skill] [bigint],
    CONSTRAINT PK_FAVORITE PRIMARY KEY([User], [Skill]),
    CONSTRAINT FK_USER FOREIGN KEY ([User]) REFERENCES [User]([Username]),
    CONSTRAINT FK_SKILL FOREIGN KEY ([Skill]) REFERENCES [Skill]([Id])
);

CREATE TABLE [dbo].[Share] (
    [Id] [bigint] IDENTITY(1,1) PRIMARY KEY,
    [ForUser] [nvarchar](16) NOT NULL,
    [FromUser] [nvarchar](16) NOT NULL,
    [Skill] [bigint] NOT NULL,
    [Read] [tinyint] NOT NULL DEFAULT 0,
    CONSTRAINT FK_FOR FOREIGN KEY ([ForUser]) REFERENCES [User]([Username]),
    CONSTRAINT FK_FROM FOREIGN KEY ([FromUser]) REFERENCES [User]([Username]),
    CONSTRAINT FK_SKILL FOREIGN KEY ([Skill]) REFERENCES [Skill]([Id]),
    CONSTRAINT FOR_FROM_DIFFER CHECK([ForUser] <> [FromUser])
);