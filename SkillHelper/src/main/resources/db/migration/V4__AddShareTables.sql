CREATE TABLE [dbo].[Share] (
    [Id] [bigint] IDENTITY(1,1) PRIMARY KEY,
    [ForUser] [nvarchar](16) NOT NULL,
    [FromUser] [nvarchar](16) NOT NULL,
    [Skill] [bigint] NOT NULL,
    [DateShared] [DATE] NOT NULL,
    [Read] [tinyint] NOT NULL DEFAULT 0,
    CONSTRAINT [SHARE_FK_FOR] FOREIGN KEY ([ForUser]) REFERENCES [User]([Username]),
    CONSTRAINT [SHARE_FK_FROM] FOREIGN KEY ([FromUser]) REFERENCES [User]([Username]),
    CONSTRAINT [SHARE_FK_SKILL] FOREIGN KEY ([Skill]) REFERENCES [Skill]([Id]),
    CONSTRAINT [SHARE_FOR_FROM_DIFFER] CHECK([ForUser] <> [FromUser])
);